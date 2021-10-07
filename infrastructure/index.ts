// import './api-gateway/index'
import './cloudwatch/index'
import './codedeploy'
import './ec2/index'
import './ecr/index'
import './ecs/index'
import './iam/index'
import './lb/index'

// Output

import { ecsService } from './ecs/services'

export const taskDefinitionArn = ecsService.taskDefinition.taskDefinition.arn
