import { ECR } from 'aws-sdk'

import { awsRegion } from './vars'

const ecr = new ECR({ apiVersion: '2015-09-21', region: awsRegion })

// prettier-ignore
const handleErrorWithDefault =
  <Type>(value: Type) => (error: Error): Type => (console.error(error), value)

/* ECR */

const extractEcrResponse = (response: ECR.DescribeImagesResponse) => ({
  imageDetails: response.imageDetails || [],
  nextToken: response.nextToken,
})

const fetchNextImages =
  (params: ECR.DescribeImagesRequest) =>
    ({ imageDetails, nextToken }): Promise<ECR.ImageDetail[]> =>
      nextToken
        ? getNextEcrImages({ ...params, nextToken: nextToken }).then((result) => [...result, ...imageDetails])
        : Promise.resolve(imageDetails)

const getNextEcrImages = (params: ECR.DescribeImagesRequest): Promise<ECR.ImageDetail[]> =>
  ecr
    .describeImages(params)
    .promise()
    .then(extractEcrResponse)
    .then(fetchNextImages(params))
    .catch(handleErrorWithDefault([]))

/* Images */

const compareImagePushDates = (mostRecentImage: ECR.ImageDetail, currentImage: ECR.ImageDetail): ECR.ImageDetail =>
  new Date(mostRecentImage.imagePushedAt ?? 0) < new Date(currentImage.imagePushedAt ?? 0)
    ? currentImage
    : mostRecentImage

const findMostRecentImage = (imageList: ECR.ImageDetail[]): ECR.ImageDetail => imageList.reduce(compareImagePushDates)

/* Tags */

export const getMostRecentImageTag = (repositoryName: string): Promise<string> =>
  getNextEcrImages({ repositoryName })
    .then(findMostRecentImage)
    .then((image) => image.imageTags[0] ?? '')
    .catch(handleErrorWithDefault(''))
