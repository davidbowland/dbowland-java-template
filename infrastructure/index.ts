import './cloudwatch/index'
import './codedeploy/index'
import './ec2/index'
import './ecr/index'
import './ecs/index'
import './iam/index'
import './lb/index'

// Output

import { ecsService } from './ecs/services'
import { loadBalancer } from './lb/load-balancers'

export const loadBalancerDnsName = loadBalancer.loadBalancer.dnsName
export const taskDefinitionArn = ecsService.taskDefinition.taskDefinition.arn
