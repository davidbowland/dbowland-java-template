import * as aws from '@pulumi/aws'

import { acmCertificateArn, domainName, createdBy, createdFor } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/domainname/

export const apiDomain = new aws.apigatewayv2.DomainName('v1-api-domain', {
  domainName,
  domainNameConfiguration: {
    certificateArn: acmCertificateArn,
    endpointType: 'REGIONAL',
    securityPolicy: 'TLS_1_2',
  },
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
