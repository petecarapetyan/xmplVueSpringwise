<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="userHistory">
        <h2 class="jh-entity-heading" data-cy="userHistoryDetailsHeading">
          <span v-text="$t('xmplVueSpringwiseApp.userHistory.detail.title')">UserHistory</span> {{ userHistory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.userHistory.name')">Name</span>
          </dt>
          <dd>
            <span>{{ userHistory.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.userHistory.issue')">Issue</span>
          </dt>
          <dd>
            <span>{{ userHistory.issue }}</span>
          </dd>
          <dt>
            <span v-text="$t('xmplVueSpringwiseApp.userHistory.issueDate')">Issue Date</span>
          </dt>
          <dd>
            <span>{{ userHistory.issueDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="userHistory.id"
          :to="{ name: 'UserHistoryEdit', params: { userHistoryId: userHistory.id } }"
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

<script lang="ts" src="./user-history-details.component.ts"></script>
