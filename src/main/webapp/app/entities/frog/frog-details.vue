<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="frog">
        <h2 class="jh-entity-heading" data-cy="frogDetailsHeading">
          <span v-text="$t('xmplVueSpringwiseApp.frog.detail.title')">Frog</span> {{ frog.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.frog.name')">Name</span>
          </dt>
          <dd>
            <span>{{ frog.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.frog.age')">Age</span>
          </dt>
          <dd>
            <span>{{ frog.age }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.frog.species')">Species</span>
          </dt>
          <dd>
            <span>{{ frog.species }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="frog.id" :to="{ name: 'FrogEdit', params: { frogId: frog.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./frog-details.component.ts"></script>
