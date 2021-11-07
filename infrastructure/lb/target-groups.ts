import { loadBalancer } from './load-balancers'
import { appName, createdBy, createdFor } from '../vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/lb/#TargetGroup

export const targetGroup1 = loadBalancer.createTargetGroup('target-group-1', {
  healthCheck: {
    path: '/actuator/health',
  },
  name: `${appName}-1`,
  port: 80,
  tags: {
    createdBy,
    createdFor,
  },
})

export const targetGroup2 = loadBalancer.createTargetGroup('target-group-2', {
  healthCheck: {
    path: '/actuator/health',
  },
  name: `${appName}-2`,
  port: 8080,
  tags: {
    createdBy,
    createdFor,
  },
})
