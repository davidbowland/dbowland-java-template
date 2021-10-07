import * as aws from '@pulumi/aws'

import { appName, createdBy, createdFor } from '../vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/codedeploy/application/

export const deployApplication = new aws.codedeploy.Application('deploy-application', {
  computePlatform: 'ECS',
  name: appName,
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
