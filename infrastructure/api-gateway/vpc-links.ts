import * as aws from '@pulumi/aws'

import { appName, createdBy, createdFor, publicSubnetId1, publicSubnetId2, securityGroupId } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/vpclink/

export const vpcLink = new aws.apigatewayv2.VpcLink('vpc-link', {
  name: appName,
  securityGroupIds: [securityGroupId],
  subnetIds: [publicSubnetId1, publicSubnetId2],
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
