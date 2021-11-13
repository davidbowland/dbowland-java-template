import * as awsx from '@pulumi/awsx'

import { vpcx } from '@ec2'
import { appName, createdBy, createdFor, publicSubnetId1, publicSubnetId2, securityGroupId } from '@vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/lb/#application-load-balancers

export const loadBalancer = new awsx.lb.ApplicationLoadBalancer('load-balancer', {
  external: false,
  name: appName,
  securityGroups: [securityGroupId],
  subnets: [publicSubnetId1, publicSubnetId2],
  tags: {
    createdBy,
    createdFor,
  },
  vpc: vpcx,
})
