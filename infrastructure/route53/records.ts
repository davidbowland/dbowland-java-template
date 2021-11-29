import * as aws from '@pulumi/aws'

import { apiDomain } from '@api-gateway'
import { domainName, hostedZoneId } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/route53/record/

export const jokesBowlandLink = new aws.route53.Record('jokes-api-bowland-link', {
  aliases: [
    {
      evaluateTargetHealth: false,
      name: apiDomain.domainNameConfiguration.targetDomainName,
      zoneId: apiDomain.domainNameConfiguration.hostedZoneId,
    },
  ],
  name: domainName,
  type: 'A',
  zoneId: hostedZoneId,
})
