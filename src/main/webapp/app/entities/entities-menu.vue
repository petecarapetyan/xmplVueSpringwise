<template>
  <div>
    <b-dropdown-item to="/student">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.student')">Student</span>
    </b-dropdown-item>
    <b-dropdown-item to="/airplane">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.airplane')">Airplane</span>
    </b-dropdown-item>
    <b-dropdown-item to="/car">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.car')">Car</span>
    </b-dropdown-item>
    <b-dropdown-item to="/truck">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.truck')">Truck</span>
    </b-dropdown-item>
    <b-dropdown-item to="/coding-category">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.codingCategory')">Coding Category</span>
    </b-dropdown-item>
    <b-dropdown-item to="/dog">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.dog')">Dog</span>
    </b-dropdown-item>
    <b-dropdown-item to="/frog">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.frog')">Frog</span>
    </b-dropdown-item>
    <b-dropdown-item to="/movie">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.movie')">Movie</span>
    </b-dropdown-item>
    <b-dropdown-item to="/spring-project">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.springProject')">Spring Project</span>
    </b-dropdown-item>
    <b-dropdown-item to="/user-history">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.userHistory')">User History</span>
    </b-dropdown-item>
    <b-dropdown-item to="/ticket">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ticket')">Ticket</span>
    </b-dropdown-item>
    <b-dropdown-item to="/score-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.scoreType')">Score Type</span>
    </b-dropdown-item>
    <b-dropdown-item to="/score">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.score')">Score</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
