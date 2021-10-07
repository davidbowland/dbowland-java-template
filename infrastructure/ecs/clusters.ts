import * as awsx from '@pulumi/awsx'

import { vpcx } from '../ec2/vpcs'
import { appName, createdBy, createdFor } from '../vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/ecs/#Cluster

export const ecsCluster = new awsx.ecs.Cluster('cluster', {
  name: appName,
  settings: [
    {
      name: 'containerInsights',
      value: 'enabled',
    },
  ],
  tags: {
    createdBy,
    createdFor,
  },
  vpc: vpcx,
})

// , publicSubnetId1, publicSubnetId2
// ecsCluster.createAutoScalingGroup('cluster-auto-scaling', {
//   templateParameters: { minSize: 1, maxSize: 2 },
//   launchConfigurationArgs: { instanceType: 't2.micro' },
//   subnetIds: [publicSubnetId1, publicSubnetId2],
//   vpc: vpcx,
// })
