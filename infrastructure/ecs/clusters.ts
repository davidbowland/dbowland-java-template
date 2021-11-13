import * as awsx from '@pulumi/awsx'

import { vpcx } from '@ec2'
import { appName, createdBy, createdFor, securityGroupId } from '@vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/ecs/#Cluster

export const ecsCluster = new awsx.ecs.Cluster('cluster', {
  name: appName,
  securityGroups: [securityGroupId],
  tags: {
    createdBy,
    createdFor,
  },
  vpc: vpcx,
})
