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

export const acmCertificateArn = `arn:aws:acm:us-east-2:${awsAccountId}:certificate/14a32175-0c26-4768-b71f-3fa611b8f5a2`
export const cognitoAppClientId = '2l1raef19n87alqce18401jrtj'
export const cognitoUserPoolEndpoint = 'https://cognito-idp.us-east-2.amazonaws.com/us-east-2_qkmykzNYy'
export const hostedZoneId = 'Z01312547RGU1BYKIJXY'

export const resourcePlain = '/users'
export const resourceById = `${resourcePlain}/{proxy+}`

/* ECS */

export const ecsDeployBucket = 'dbowland-ecs-deploy'
export const ecsLogGroupName = `/ecs/${appName}-log-group`
export const publicSubnetId1 = 'subnet-0a8968a14bdc0dfb8'
export const publicSubnetId2 = 'subnet-031d651d972860c51'
export const securityGroupId = 'sg-0c42a8fdfdb24da0a'
export const vpcId = 'vpc-083ca80494f3beafe'
