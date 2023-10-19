<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="springProject">
        <h2 class="jh-entity-heading" data-cy="springProjectDetailsHeading">
          <span v-text="$t('xmplVueSpringwiseApp.springProject.detail.title')">SpringProject</span> {{ springProject.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.springProject.title')">Title</span>
          </dt>
          <dd>
            <span>{{ springProject.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.springProject.description')">Description</span>
          </dt>
          <dd>
            <span>{{ springProject.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.springProject.imagePath')">Image Path</span>
          </dt>
          <dd>
            <span>{{ springProject.imagePath }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.springProject.url')">Url</span>
          </dt>
          <dd>
            <span>{{ springProject.url }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="springProject.id"
          :to="{ name: 'SpringProjectEdit', params: { springProjectId: springProject.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./spring-project-details.component.ts"></script>
