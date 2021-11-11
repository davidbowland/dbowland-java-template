import * as aws from '@pulumi/aws'
import * as awsx from '@pulumi/awsx'

import { vpcId } from '@vars'

// https://www.pulumi.com/docs/reference/pkg/aws/ec2/vpc/
// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/ec2/

export const vpc = aws.ec2.Vpc.get('vpc', vpcId)

export const vpcx = new awsx.ec2.Vpc('vpcx', { vpc: vpc })
