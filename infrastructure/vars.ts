import * as path from 'path'

const directories = path.dirname(__dirname).split(path.sep)

/* General */

export const appName = directories[directories.length - 1]
export const awsAccountId = process.env.AWS_ACCOUNT_ID
export const awsRegion = 'us-east-2'
export const domainName = 'java-template.bowland.link'

export const createdBy = appName
export const createdFor = 'example-project'

/* API Gateway */

export const acmCertificateArn = 'arn:aws:acm:us-east-2:494887012091:certificate/14a32175-0c26-4768-b71f-3fa611b8f5a2'
export const cognitoAppClientId = '20qc4slu4pbciub20544gesu6b'
export const cognitoUserPoolEndpoint = 'https://cognito-idp.us-east-2.amazonaws.com/us-east-2_JLHXqBLCP'
export const hostedZoneId = 'Z01312547RGU1BYKIJXY'

export const resourcePlain = '/users'
export const resourceById = `${resourcePlain}/{proxy+}`

/* ECS */

export const ecsDeployBucket = 'jokes-ecs-deploy'
export const ecsLogGroupName = `/ecs/${appName}-log-group`
export const publicSubnetId1 = 'subnet-0017bd402a7fece95'
export const publicSubnetId2 = 'subnet-0551177223393a2fa'
export const securityGroupId = 'sg-040b366b2321850be'
export const vpcId = 'vpc-0ba6acd3af81bfa46'
