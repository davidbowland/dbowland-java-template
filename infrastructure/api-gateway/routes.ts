import * as aws from '@pulumi/aws'
import * as pulumi from '@pulumi/pulumi'

import { api } from './apis'
import { integration } from './integrations'
import { resourceById, resourcePlain } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/route/

export const deleteId = new aws.apigatewayv2.Route('delete-id', {
  apiId: api.id,
  routeKey: `DELETE ${resourceById}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const getId = new aws.apigatewayv2.Route('get-id', {
  apiId: api.id,
  routeKey: `GET ${resourceById}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const getPlain = new aws.apigatewayv2.Route('get-plain', {
  apiId: api.id,
  routeKey: `GET ${resourcePlain}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const patchId = new aws.apigatewayv2.Route('patch-id', {
  apiId: api.id,
  routeKey: `PATCH ${resourceById}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const postPlain = new aws.apigatewayv2.Route('post-plain', {
  apiId: api.id,
  routeKey: `POST ${resourcePlain}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const putId = new aws.apigatewayv2.Route('put-id', {
  apiId: api.id,
  routeKey: `PUT ${resourceById}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})
