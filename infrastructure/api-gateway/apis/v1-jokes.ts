import * as awsx from '@pulumi/awsx'

import { loadBalancer } from '../../lb/load-balancers'
import { cognitoUserPoolArn, resourceById, resourcePlain, resourceRandom } from '../../vars'
import { serviceLink } from '../vpc-links'

// https://www.pulumi.com/docs/reference/pkg/nodejs/pulumi/awsx/apigateway/

const cognitoAuthorizer = awsx.apigateway.getCognitoAuthorizer({
  providerARNs: [cognitoUserPoolArn],
})

const loadBalancerTarget = {
  connectionId: serviceLink.id,
  connectionType: 'VPC_LINK',
  type: 'aws',
  uri: `http://${loadBalancer.loadBalancer.dnsName}`
}

export const jokesApi = new awsx.apigateway.API('service-jokes-api-v1', {
  stageName: 'v1',
  routes: [
    // By ID
    {
      authorizers: [cognitoAuthorizer],
      path: resourceById,
      method: 'GET',
      target: loadBalancerTarget,
    },
    {
      authorizers: [cognitoAuthorizer],
      path: resourceById,
      method: 'PUT',
      eventHandler: serviceLink,
    },
    {
      authorizers: [cognitoAuthorizer],
      path: resourceById,
      method: 'DELETE',
      eventHandler: serviceLink,
    },
    {
      path: resourceById,
      method: 'OPTIONS',
      eventHandler: zip_v1_jokes_handler,
    },
    // Plain
    {
      authorizers: [cognitoAuthorizer],
      path: resourcePlain,
      method: 'GET',
      eventHandler: zip_v1_jokes_handler,
    },
    {
      authorizers: [cognitoAuthorizer],
      path: resourcePlain,
      method: 'POST',
      eventHandler: zip_v1_jokes_handler,
    },
    {
      path: resourcePlain,
      method: 'OPTIONS',
      eventHandler: zip_v1_jokes_handler,
    },
    // Random
    {
      path: resourceRandom,
      method: 'GET',
      eventHandler: zip_v1_jokes_handler,
    },
    {
      path: resourceRandom,
      method: 'OPTIONS',
      eventHandler: zip_v1_jokes_handler,
    },
  ],
  gatewayResponses: {
    UNAUTHORIZED: {
      statusCode: 401,
      responseTemplates: {
        'application/json': '{"message":$context.error.messageString}',
      },
      responseParameters: {
        'gatewayresponse.header.Access-Control-Allow-Origin': "'*'",
        'gatewayresponse.header.Access-Control-Allow-Headers':
          "'Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token'",
        'gatewayresponse.header.Access-Control-Allow-Methods': "'OPTIONS,GET,POST,PUT,DELETE'",
        'gatewayresponse.header.Access-Control-Allow-Credentials': "'*'",
      },
    },
  },
})
