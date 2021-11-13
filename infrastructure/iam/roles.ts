import * as aws from '@pulumi/aws'

import { appName, createdBy, createdFor } from '@vars'

// https://www.pulumi.com/docs/reference/pkg/aws/iam/role/

export const codeDeployServiceRole = new aws.iam.Role('codedeploy-service', {
  assumeRolePolicy: {
    Version: '2012-10-17',
    Statement: [
      {
        Sid: '',
        Effect: 'Allow',
        Principal: {
          Service: ['codedeploy.amazonaws.com'],
        },
        Action: 'sts:AssumeRole',
      },
    ],
  },
  name: `${appName}-codedeploy`,
  tags: {
    createdBy,
    createdFor,
  },
})

export const ecsTaskExecutionRole = new aws.iam.Role('ecs-task-execution', {
  assumeRolePolicy: {
    Version: '2012-10-17',
    Statement: [
      {
        Sid: '',
        Effect: 'Allow',
        Principal: {
          Service: ['ecs-tasks.amazonaws.com'],
        },
        Action: 'sts:AssumeRole',
      },
    ],
  },
  name: `${appName}-ecs-task-execution`,
  tags: {
    createdBy,
    createdFor,
  },
})
