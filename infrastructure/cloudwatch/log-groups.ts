import * as aws from '@pulumi/aws'

import { createdBy, createdFor, ecsLogGroupName } from '../vars'

// https://www.pulumi.com/docs/reference/pkg/aws/cloudwatch/loggroup/

export const logGroup = new aws.cloudwatch.LogGroup('log-group', {
  name: ecsLogGroupName,
  retentionInDays: 14,
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
