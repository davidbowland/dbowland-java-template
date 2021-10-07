import { loadBalancer } from './load-balancers'
import { targetGroup1 } from './target-groups'
import { appName } from '../vars'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/lb/#ApplicationListener

export const applicationListener = loadBalancer.createListener('application-listener', {
  name: appName,
  port: 80,
  targetGroup: targetGroup1,
})
