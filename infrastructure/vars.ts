import * as path from 'path'

const directories = path.dirname(__dirname).split(path.sep)

/* General */

export const appName = directories[directories.length - 1]
export const awsAccountId = process.env.AWS_ACCOUNT_ID
export const awsRegion = 'us-east-2'

export const createdBy = appName
export const createdFor = 'example-project'

/* API Gateway */

export const cognitoUserPoolArn = `arn:aws:cognito-idp:us-east-2:${awsAccountId}:userpool/us-east-2_JLHXqBLCP`

export const resourcePlain = '/users'
export const resourceById = `${resourcePlain}/{proxy+}`

/* ECS */

export const ecsDeployBucket = 'jokes-ecs-deploy'
export const ecsLogGroupName = `/ecs/${appName}-log-group`
export const publicSubnetId1 = 'subnet-0017bd402a7fece95'
export const publicSubnetId2 = 'subnet-0551177223393a2fa'
export const securityGroupId = 'sg-040b366b2321850be'
export const vpcId = 'vpc-0ba6acd3af81bfa46'
