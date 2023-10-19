<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="truck">
        <h2 class="jh-entity-heading" data-cy="truckDetailsHeading">
          <span v-text="$t('xmplVueSpringwiseApp.truck.detail.title')">Truck</span> {{ truck.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.truck.modelName')">Model Name</span>
          </dt>
          <dd>
            <span>{{ truck.modelName }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.truck.make')">Make</span>
          </dt>
          <dd>
            <span>{{ truck.make }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.truck.motorSize')">Motor Size</span>
          </dt>
          <dd>
            <span>{{ truck.motorSize }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.truck.color')">Color</span>
          </dt>
          <dd>
            <span>{{ truck.color }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="truck.id" :to="{ name: 'TruckEdit', params: { truckId: truck.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./truck-details.component.ts"></script>
