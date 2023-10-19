<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="car">
        <h2 class="jh-entity-heading" data-cy="carDetailsHeading">
          <span v-text="$t('xmplVueSpringwiseApp.car.detail.title')">Car</span> {{ car.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.car.motorSize')">Motor Size</span>
          </dt>
          <dd>
            <span>{{ car.motorSize }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.car.modelName')">Model Name</span>
          </dt>
          <dd>
            <span>{{ car.modelName }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.car.wheelSize')">Wheel Size</span>
          </dt>
          <dd>
            <span>{{ car.wheelSize }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.car.transmission')">Transmission</span>
          </dt>
          <dd>
            <span>{{ car.transmission }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.car.color')">Color</span>
          </dt>
          <dd>
            <span>{{ car.color }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.car.yearOf')">Year Of</span>
          </dt>
          <dd>
            <span>{{ car.yearOf }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.car.price')">Price</span>
          </dt>
          <dd>
            <span>{{ car.price }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="car.id" :to="{ name: 'CarEdit', params: { carId: car.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./car-details.component.ts"></script>
