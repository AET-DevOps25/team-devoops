# 🍲 - Problem Statement

Many students at TUM, particularly in the larger study programs, report feeling lonely and isolated despite having countless peers. One of the places this is especially noticeable is at the Mensa during lunchtime. It's common for students that don't have a strong network of friends at university to eat alone, or avoid eating at the mensa entirely to avoid feeling socially awkward.

## ⚙️ - Functionality

Our tool will focus on transforming the potentially uncomfortable situation of eating alone at the mensa into an oportunity to meet fellow students and make new friends. In order to enable this, our tool should offer the following features:

### Sign Up
In order to match students, our tool needs to gather some basic information about them first. In order to do this, users can register what times they plan to have lunch, as well as information about their study program, courses they are taking, and hobbies they enjoy. For the first version of our app, we won't require users to sign up with their TUM account, but might consider it in the development process.

### Matching
Students on the same table should have some things in common to talk about. Our system will match students with similar interests that are having lunch at the same time, and invite them to share a table. Users will also be able limit their search by the specified characteristics, e.g. if they are only willing to meet up people from the same degree or from the same age group. Each user that submits a search request in our app will be presented with a list of candidates they can send invites to. The system will also make it possible to create lunch groups with more than 2 people. 

### RSVP {Note: Maybe not?}
Nothing is worse than waiting for someone who won't show up, thus our tool will ask users to RSVP to any invites they may recieve. Once an invite is accepted, both users will receive an email with a corresponding ics. file in the attachment. It will also be possible to postpone or cancel the meetup. Users who will have submitted a search request but still wouldn't find a lunch partner will be matched randomly (within their search criteria) 1 hour before their lunchtime.

### Conversation Starters
The first few words are always the hardest. To help ease that, our system's GenAI component will suggest topics of conversation based on the students common interests to help break the ice.

### Chat
The mensa is a big place, and it can be hard to find someone you haven't seen yet. Therefore, our tool will enable users to chat with one another in order to arrange a meeting place, or let each other now if they may be running late.

## 🤸 - Intended Users

Our tool is targeted at TUM students in general, especially those with a smaller network of friends. Often, these are students who are new to University, or who may struggle to socialize for any reason.

## ✨ - GenAI Integration

Meeting strangers can be awkward especially at the start. In order to counteract this awkwardness, we will use Generative AI to act as a mediator to help break the ice by suggesting potential topics of conversation based on student's shared interests. 

## 🎭 - Scenarios

### Scenario 1
James is a first semester M.Sc. Informatics student. Unfortunately, he wasn't able to be in Munich for the SET in the first few weeks and thus hasn't really made any friends.

James is comfortable eating alone at the Mensa, but he'd like to meet some of his fellow students, so he logs onto Mensa-Match and inputs the times he plans to have lunch, what degree he is studying, what classes he's taking, and what his hobbies are outside of university. James doesn't have any preferences on whom to meet, so he leaves the filter fields empty and leaves it to the chance.

On Monday, an hour before lunchtime, James recieves a notification that he's been matched with a table of other students for lunch at the mensa. James accepts the invitation, letting the system know he plans on joining. He receives an email with an ics. file that he happily adds to his calendar, as he loves technology and keeping everything documented.

At the agreed time, James arrives at the Mensa, but can't find the students he's supposed to meet with. Upon opening the chat function, he sees another user has sent a message saying they've arrived early, and already have a table.

James gets his food, and joins the other students at the table. Not knowing how where to begin, he checks the list of conversation starters and sees that several students are also taking the DevOps course this semester. With this in mind, James asks the other students about their projects, and exchanges some jokes about Scrum masters.

After an hour of pleasent conversation, James has now met several new people, and goes about his day feeling less lonely.

### Scenario 2
TODO: Anastasiia limits the search to women in CS only and receives an invite

### Scenario 3
TODO: Enrico wants to meet people outside of Computer Science that are doing the Interdisciplinary Project this semester, as he's looking for group members. He sends out several invitations. 
