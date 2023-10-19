import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Student = () => import('@/entities/student/student.vue');
// prettier-ignore
const StudentUpdate = () => import('@/entities/student/student-update.vue');
// prettier-ignore
const StudentDetails = () => import('@/entities/student/student-details.vue');
// prettier-ignore
const Airplane = () => import('@/entities/airplane/airplane.vue');
// prettier-ignore
const AirplaneUpdate = () => import('@/entities/airplane/airplane-update.vue');
// prettier-ignore
const AirplaneDetails = () => import('@/entities/airplane/airplane-details.vue');
// prettier-ignore
const Car = () => import('@/entities/car/car.vue');
// prettier-ignore
const CarUpdate = () => import('@/entities/car/car-update.vue');
// prettier-ignore
const CarDetails = () => import('@/entities/car/car-details.vue');
// prettier-ignore
const Truck = () => import('@/entities/truck/truck.vue');
// prettier-ignore
const TruckUpdate = () => import('@/entities/truck/truck-update.vue');
// prettier-ignore
const TruckDetails = () => import('@/entities/truck/truck-details.vue');
// prettier-ignore
const CodingCategory = () => import('@/entities/coding-category/coding-category.vue');
// prettier-ignore
const CodingCategoryUpdate = () => import('@/entities/coding-category/coding-category-update.vue');
// prettier-ignore
const CodingCategoryDetails = () => import('@/entities/coding-category/coding-category-details.vue');
// prettier-ignore
const Dog = () => import('@/entities/dog/dog.vue');
// prettier-ignore
const DogUpdate = () => import('@/entities/dog/dog-update.vue');
// prettier-ignore
const DogDetails = () => import('@/entities/dog/dog-details.vue');
// prettier-ignore
const Frog = () => import('@/entities/frog/frog.vue');
// prettier-ignore
const FrogUpdate = () => import('@/entities/frog/frog-update.vue');
// prettier-ignore
const FrogDetails = () => import('@/entities/frog/frog-details.vue');
// prettier-ignore
const Movie = () => import('@/entities/movie/movie.vue');
// prettier-ignore
const MovieUpdate = () => import('@/entities/movie/movie-update.vue');
// prettier-ignore
const MovieDetails = () => import('@/entities/movie/movie-details.vue');
// prettier-ignore
const SpringProject = () => import('@/entities/spring-project/spring-project.vue');
// prettier-ignore
const SpringProjectUpdate = () => import('@/entities/spring-project/spring-project-update.vue');
// prettier-ignore
const SpringProjectDetails = () => import('@/entities/spring-project/spring-project-details.vue');
// prettier-ignore
const UserHistory = () => import('@/entities/user-history/user-history.vue');
// prettier-ignore
const UserHistoryUpdate = () => import('@/entities/user-history/user-history-update.vue');
// prettier-ignore
const UserHistoryDetails = () => import('@/entities/user-history/user-history-details.vue');
// prettier-ignore
const Ticket = () => import('@/entities/ticket/ticket.vue');
// prettier-ignore
const TicketUpdate = () => import('@/entities/ticket/ticket-update.vue');
// prettier-ignore
const TicketDetails = () => import('@/entities/ticket/ticket-details.vue');
// prettier-ignore
const ScoreType = () => import('@/entities/score-type/score-type.vue');
// prettier-ignore
const ScoreTypeUpdate = () => import('@/entities/score-type/score-type-update.vue');
// prettier-ignore
const ScoreTypeDetails = () => import('@/entities/score-type/score-type-details.vue');
// prettier-ignore
const Score = () => import('@/entities/score/score.vue');
// prettier-ignore
const ScoreUpdate = () => import('@/entities/score/score-update.vue');
// prettier-ignore
const ScoreDetails = () => import('@/entities/score/score-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'student',
      name: 'Student',
      component: Student,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/new',
      name: 'StudentCreate',
      component: StudentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/:studentId/edit',
      name: 'StudentEdit',
      component: StudentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/:studentId/view',
      name: 'StudentView',
      component: StudentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'airplane',
      name: 'Airplane',
      component: Airplane,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'airplane/new',
      name: 'AirplaneCreate',
      component: AirplaneUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'airplane/:airplaneId/edit',
      name: 'AirplaneEdit',
      component: AirplaneUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'airplane/:airplaneId/view',
      name: 'AirplaneView',
      component: AirplaneDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'car',
      name: 'Car',
      component: Car,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'car/new',
      name: 'CarCreate',
      component: CarUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'car/:carId/edit',
      name: 'CarEdit',
      component: CarUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'car/:carId/view',
      name: 'CarView',
      component: CarDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'truck',
      name: 'Truck',
      component: Truck,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'truck/new',
      name: 'TruckCreate',
      component: TruckUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'truck/:truckId/edit',
      name: 'TruckEdit',
      component: TruckUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'truck/:truckId/view',
      name: 'TruckView',
      component: TruckDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coding-category',
      name: 'CodingCategory',
      component: CodingCategory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coding-category/new',
      name: 'CodingCategoryCreate',
      component: CodingCategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coding-category/:codingCategoryId/edit',
      name: 'CodingCategoryEdit',
      component: CodingCategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coding-category/:codingCategoryId/view',
      name: 'CodingCategoryView',
      component: CodingCategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dog',
      name: 'Dog',
      component: Dog,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dog/new',
      name: 'DogCreate',
      component: DogUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dog/:dogId/edit',
      name: 'DogEdit',
      component: DogUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dog/:dogId/view',
      name: 'DogView',
      component: DogDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'frog',
      name: 'Frog',
      component: Frog,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'frog/new',
      name: 'FrogCreate',
      component: FrogUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'frog/:frogId/edit',
      name: 'FrogEdit',
      component: FrogUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'frog/:frogId/view',
      name: 'FrogView',
      component: FrogDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'movie',
      name: 'Movie',
      component: Movie,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'movie/new',
      name: 'MovieCreate',
      component: MovieUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'movie/:movieId/edit',
      name: 'MovieEdit',
      component: MovieUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'movie/:movieId/view',
      name: 'MovieView',
      component: MovieDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'spring-project',
      name: 'SpringProject',
      component: SpringProject,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'spring-project/new',
      name: 'SpringProjectCreate',
      component: SpringProjectUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'spring-project/:springProjectId/edit',
      name: 'SpringProjectEdit',
      component: SpringProjectUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'spring-project/:springProjectId/view',
      name: 'SpringProjectView',
      component: SpringProjectDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-history',
      name: 'UserHistory',
      component: UserHistory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-history/new',
      name: 'UserHistoryCreate',
      component: UserHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-history/:userHistoryId/edit',
      name: 'UserHistoryEdit',
      component: UserHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-history/:userHistoryId/view',
      name: 'UserHistoryView',
      component: UserHistoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket',
      name: 'Ticket',
      component: Ticket,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket/new',
      name: 'TicketCreate',
      component: TicketUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket/:ticketId/edit',
      name: 'TicketEdit',
      component: TicketUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket/:ticketId/view',
      name: 'TicketView',
      component: TicketDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score-type',
      name: 'ScoreType',
      component: ScoreType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score-type/new',
      name: 'ScoreTypeCreate',
      component: ScoreTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score-type/:scoreTypeId/edit',
      name: 'ScoreTypeEdit',
      component: ScoreTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score-type/:scoreTypeId/view',
      name: 'ScoreTypeView',
      component: ScoreTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score',
      name: 'Score',
      component: Score,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score/new',
      name: 'ScoreCreate',
      component: ScoreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score/:scoreId/edit',
      name: 'ScoreEdit',
      component: ScoreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score/:scoreId/view',
      name: 'ScoreView',
      component: ScoreDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
