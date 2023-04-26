REGISTER: 
1. Register : allow users to register with an unique username, a first name and a
   last name, an e-mail and a password
2. Search : search for other users by username, firstname or lastname
3. Follow : follow another user and start receiving his public posts
4. Unfollow : unfollow a user and stop receiving feeds from this user
5. Unregister : remove user and all his posts

POST:
1. Add post : post a public message
2. Get own posts : return all posts added by the current user. Able to filter posts newer
   than a timestamp
3. Get feed : return all posts added by users followed by the current user
4. Delete post
5. Repost : “copy” an existing post from a different user
6. Get mentions : return all posts in which the current user was mentioned

LIKE: 
1. Like post : mark an existing post with a like. The owner of the post will receive in his
   “get own posts” call (Posts.2) ok each post, a list of all the likes that message
   received
2. Remove like
3. Cascade: When a post is deleted, remove all its likes

REPLY:
1. Add post reply : reply to existing posts or other replies. Able to specify if the reply
   is public or only for the parent post’s owner
2. Cascade: When a parent post is deleted, remove all its replies

user.setPassword(passwordEncoder.encode(request.getPassword()));
private final PasswordEncoder passwordEncoder;
MyRole userRole = roleRepository.findByName(RoleName.ROLE_USER);
user.setRoles(Set.of(userRole));