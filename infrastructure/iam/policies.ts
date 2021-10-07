import * as aws from '@pulumi/aws'

import { appName, createdBy, createdFor, ecsDeployBucket } from '../vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/iam/policy/

export const codeDeployPolicy = new aws.iam.Policy('code-deploy-policy', {
  description: 'CodeDeploy policy',
  name: `${appName}-code-deploy-policy`,
  path: '/',
  policy: JSON.stringify({
    Version: '2012-10-17',
    Statement: [
      {
        Effect: 'Allow',
        Action: ['s3:ListBucket'],
        Resource: [`arn:aws:s3:::${ecsDeployBucket}`],
      },
      {
        Effect: 'Allow',
        Action: ['s3:GetObject'],
        Resource: [`arn:aws:s3:::${ecsDeployBucket}/${appName}/*`],
      },
    ],
  }),
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})

export const ecsTaskExecutionPolicy = new aws.iam.Policy('ecs-task-execution-policy', {
  description: 'ECS task execution policy',
  name: `${appName}-ecs-task-execution-policy`,
  path: '/',
  policy: JSON.stringify({
    Version: '2012-10-17',
    Statement: [
      {
        Effect: 'Allow',
        Action: [
          'ecr:GetAuthorizationToken',
          'ecr:BatchCheckLayerAvailability',
          'ecr:GetDownloadUrlForLayer',
          'ecr:BatchGetImage',
          'ecr:*',
          'logs:CreateLogStream',
          'logs:PutLogEvents',
          'secretsmanager:GetSecretValue',
          'secretsmanager:*',
          'ssm:*',
        ],
        Resource: '*',
      },
      {
        Effect: 'Allow',
        Action: 'sts:AssumeRole',
        Resource: '*',
      },
    ],
  }),
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
