<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="airplane">
        <h2 class="jh-entity-heading" data-cy="airplaneDetailsHeading">
          <span v-text="$t('xmplVueSpringwiseApp.airplane.detail.title')">Airplane</span> {{ airplane.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.airplane.model')">Model</span>
          </dt>
          <dd>
            <span>{{ airplane.model }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.airplane.make')">Make</span>
          </dt>
          <dd>
            <span>{{ airplane.make }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.airplane.color')">Color</span>
          </dt>
          <dd>
            <span>{{ airplane.color }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="airplane.id" :to="{ name: 'AirplaneEdit', params: { airplaneId: airplane.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./airplane-details.component.ts"></script>
