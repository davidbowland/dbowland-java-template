import * as awsx from '@pulumi/awsx'

import { ecsCluster } from './clusters'
import { applicationListener } from '../lb/listeners'
import {
  appName,
  awsAccountId,
  awsRegion,
  createdBy,
  createdFor,
  ecsLogGroupName,
  publicSubnetId1,
  publicSubnetId2,
} from '../vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/ecs/#services

// The following *resource* name -- not service name -- determines Task Definition name
export const ecsService = new awsx.ecs.FargateService(`${appName}-service`, {
  cluster: ecsCluster,
  deploymentController: {
    type: 'CODE_DEPLOY',
  },
  deploymentMaximumPercent: 200,
  deploymentMinimumHealthyPercent: 0,
  desiredCount: 1,
  enableEcsManagedTags: false,
  name: `${appName}-service`,
  propagateTags: 'SERVICE',
  taskDefinitionArgs: {
    containers: {
      [`${appName}-container`]: {
        cpu: 1,
        memory: 2048,
        image: `${awsAccountId}.dkr.ecr.${awsRegion}.amazonaws.com/${appName}:latest`,
        portMappings: [applicationListener],
        logConfiguration: {
          logDriver: 'awslogs',
          options: {
            'awslogs-group': ecsLogGroupName,
            'awslogs-region': awsRegion,
            'awslogs-stream-prefix': 'ecs',
          },
        },
      },
    },
    tags: {
      createdBy,
      createdFor,
    },
  },
  subnets: [publicSubnetId1, publicSubnetId2],
  tags: {
    createdBy,
    createdFor,
  },
  waitForSteadyState: false,
})
