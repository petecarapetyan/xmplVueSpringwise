<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="dog">
        <h2 class="jh-entity-heading" data-cy="dogDetailsHeading">
          <span v-text="$t('xmplVueSpringwiseApp.dog.detail.title')">Dog</span> {{ dog.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.dog.name')">Name</span>
          </dt>
          <dd>
            <span>{{ dog.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.dog.age')">Age</span>
          </dt>
          <dd>
            <span>{{ dog.age }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.dog.breed')">Breed</span>
          </dt>
          <dd>
            <span>{{ dog.breed }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="dog.id" :to="{ name: 'DogEdit', params: { dogId: dog.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dog-details.component.ts"></script>
