import * as aws from '@pulumi/aws'

import { appName, createdBy, createdFor } from '../vars'

// https://www.pulumi.com/docs/reference/pkg/aws/ecr/repository/

export const imageRepository = new aws.ecr.Repository('image-repository', {
  imageScanningConfiguration: {
    scanOnPush: true,
  },
  imageTagMutability: 'MUTABLE',
  name: appName,
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
