<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="movie">
        <h2 class="jh-entity-heading" data-cy="movieDetailsHeading">
          <span v-text="$t('xmplVueSpringwiseApp.movie.detail.title')">Movie</span> {{ movie.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.movie.name')">Name</span>
          </dt>
          <dd>
            <span>{{ movie.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.movie.yearOf')">Year Of</span>
          </dt>
          <dd>
            <span>{{ movie.yearOf }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.movie.genre')">Genre</span>
          </dt>
          <dd>
            <span>{{ movie.genre }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.movie.rating')">Rating</span>
          </dt>
          <dd>
            <span>{{ movie.rating }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="movie.id" :to="{ name: 'MovieEdit', params: { movieId: movie.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./movie-details.component.ts"></script>
