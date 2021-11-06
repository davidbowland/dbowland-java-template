import { writeFileSync } from 'fs'

import { ecsService } from '../../ecs/services'
import { appName, publicSubnetId1, publicSubnetId2, securityGroupId } from '../../vars'

ecsService.taskDefinition.taskDefinition.arn.apply((taskDefinitionArn) =>
  writeFileSync(
    `${__dirname}/AppSpec.json`,
    JSON.stringify({
      version: 1.0,
      Resources: [
        {
          TargetService: {
            Type: 'AWS::ECS::Service',
            Properties: {
              TaskDefinition: taskDefinitionArn,
              LoadBalancerInfo: {
                ContainerName: `${appName}-container`,
                ContainerPort: 80,
              },
              PlatformVersion: 'LATEST',
              NetworkConfiguration: {
                AwsvpcConfiguration: {
                  Subnets: [publicSubnetId1, publicSubnetId2],
                  SecurityGroups: [securityGroupId],
                  AssignPublicIp: 'ENABLED',
                },
              },
            },
          },
        },
      ],
    })
  )
)
