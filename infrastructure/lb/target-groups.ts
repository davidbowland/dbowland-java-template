import { loadBalancer } from './load-balancers'
import { appName } from '../vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/lb/#TargetGroup

export const targetGroup1 = loadBalancer.createTargetGroup('target-group-1', {
  name: `${appName}-1`,
  port: 80,
})

export const targetGroup2 = loadBalancer.createTargetGroup('target-group-2', {
  name: `${appName}-2`,
  port: 8080,
})
