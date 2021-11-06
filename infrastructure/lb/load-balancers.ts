import * as awsx from '@pulumi/awsx'

import { vpcx } from '../ec2/vpcs'
import { appName, publicSubnetId1, publicSubnetId2, securityGroupId } from '../vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/lb/#application-load-balancers

export const loadBalancer = new awsx.lb.ApplicationLoadBalancer('load-balancer', {
  external: true,
  name: appName,
  securityGroups: [securityGroupId],
  subnets: [publicSubnetId1, publicSubnetId2],
  vpc: vpcx,
})
