import './api-gateway'
import './cloudwatch'
import './codedeploy'
import './ec2'
import './ecr'
import './ecs'
import './iam'
import './lb'

// Output

import { ecsService } from './ecs/services'
import { loadBalancer } from './lb/load-balancers'

export const loadBalancerDnsName = loadBalancer.loadBalancer.dnsName
export const taskDefinitionArn = ecsService.taskDefinition.taskDefinition.arn
