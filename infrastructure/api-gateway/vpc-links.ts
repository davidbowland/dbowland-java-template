import * as aws from '@pulumi/aws'
import { loadBalancer } from '../lb/load-balancers'

import { appName, createdBy, createdFor } from '../vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigateway/vpclink/

export const serviceLink = new aws.apigateway.VpcLink('service-link', {
  name: appName,
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
  targetArn: loadBalancer.loadBalancer.arn,
})
