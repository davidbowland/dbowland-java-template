import * as awsx from '@pulumi/awsx'

import { vpcx } from '../ec2/vpcs'
import { appName, createdBy, createdFor, publicSubnetId1, publicSubnetId2 } from '../vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/lb/#application-load-balancers

export const loadBalancer = new awsx.lb.NetworkLoadBalancer('load-balancer', {
  external: true,
  name: appName,
  subnets: [publicSubnetId1, publicSubnetId2],
  tags: {
    createdBy,
    createdFor,
  },
  vpc: vpcx,
})
