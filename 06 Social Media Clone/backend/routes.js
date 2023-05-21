import { signUp, logIn, checkIfLoggedIn, createPost, getPosts, deletePost, editPost, 
  searchPeople, sendFriendRequest, getFriendRequests, answerFriendRequest, getFriends} from "./controller.js";

const setUpRoutes = (app) => {
  app.post("/signup", signUp);
  app.post("/login", logIn);
  app.post("/checkifloggedin", checkIfLoggedIn);
  app.post("/get-posts", getPosts);
  app.post("/create-post", createPost);
  app.post("/edit-post", editPost);
  app.post("/delete-post", deletePost);
  app.post("/search-people", searchPeople);
  app.post("/send-friend-request", sendFriendRequest);
  app.post("/get-friend-requests", getFriendRequests);
  app.post("/answer-friend-request", answerFriendRequest);
  app.post("/get-friends", getFriends);
}

export default setUpRoutes;