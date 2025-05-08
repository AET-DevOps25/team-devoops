# 🍲 - Problem Statement

Many students at TUM, particularly in the larger study programs, report feeling lonely and isolated despite having countless peers. One of the places where this is especially noticeable is the Mensa during lunchtime. It's common for students that don't have a strong network of friends at university to eat alone or avoid eating at the Mensa entirely to not feel socially awkward.

## ⚙️ - Functionality

Our tool will focus on transforming the potentially uncomfortable situation of eating alone at the Mensa into an opportunity to meet fellow students and make new friends. In order to enable this, our tool should offer the following features:

### Signing Up
In order to match students, our tool needs to gather some basic information about them first. In order to do this, users can specify what times they plan to have lunch and at which Mensa, as well as some personal data, such as their name, age, language, gender, study program, current semester, courses they are taking, and hobbies they enjoy. All personal data fields are optional. For the first version of our tool, we won't require users to sign up with their TUM account but might consider it in the development process.

### Matching
Our system will match students into groups 1 hour before their common lunchtime, and invite them to share a table. Users can choose how large they'd like their group to be - depending on whether they'd like to get to know one person at a time or an entire bunch. They will also be able to limit their search by specified characteristics, e.g. if they are only willing to meet up with people from the same degree or with the same hobby.

### RSVP-ing
Nothing is worse than waiting for someone who won't show up, thus our tool will ask users to RSVP to any invites they may receive (with ```yes``` / ```no``` options, no tentatives). Once an invite is accepted, all users will receive a confirmation email with a corresponding ```ics.``` file in the attachment. It will also be possible to postpone or cancel the meetup.

### Meeting up
The Mensa is a big place, and it can be hard to find someone you haven't seen yet. Therefore, our tool will enable users to chat with one another in order to arrange a meeting place, or let each other know they may be running late. Students at the same table should also have some things to talk about, so to help ease the conversation start, our system's GenAI component will suggest topics based on the students' common interests to help break the ice.

## 🤸 - Intended Users

Our tool is targeted at TUM students in general, especially those with a smaller network of friends. Often, these are students who are new to university, or who may struggle to socialize for any reason. However, all TUM members are welcome to use the tool.

## ✨ - GenAI Integration

Meeting strangers can be awkward, especially at the start. In order to counteract this awkwardness, we will use Generative AI to act as a mediator to help break the ice by suggesting potential topics of conversation based on student's shared interests.

## 🎭 - Use Case Scenarios

### Scenario 1
James is a first-semester M.Sc. Informatics student. Unfortunately, he wasn't able to be in Munich for the SET in the first few weeks and thus hasn't really made any friends.

James is comfortable eating alone at the Mensa, but he'd like to meet some of his fellow students, so he logs onto Mensa-Match and inputs the times he plans to have lunch at the Garching Mensa, what degree he is studying, what classes he's taking, and what his hobbies are outside of university. James doesn't have any preferences on whom to meet, so he sets no search criteria and leaves it to chance.

On Monday, an hour before lunchtime, James receives a notification that he's been matched with a table of other students for lunch at the Mensa. James accepts the invitation, letting the system know he plans on joining. He receives a confirmation email with an ```ics.``` file that he happily adds to his calendar, as he loves technology and keeping everything documented.

At the agreed time, James arrives at the Mensa, but can't find the students he's supposed to meet with. Upon opening the chat function, he sees another user has sent a message saying they've arrived early, and already have a table. James gets his food and joins the other students at the table. Not knowing how where to begin, he checks the list of conversation starters and sees that several students are also taking the DevOps course this semester. With this in mind, James asks the other students about their projects and exchanges some jokes about Scrum masters.

After an hour of pleasant conversation, James has now met several new people and goes about his day feeling less lonely.

### Scenario 2
Anastasiia is also a first-semester M.Sc. Informatics student. She met a lot of wonderful people during the orientation week but noticed there were barely any female students in her degree. Being new to Munich and wanting to connect with other women in Computer Science, Anastasiia signs up for Mensa-Match.

She fills out the information about her studies and hobbies, and specifies she would like to have lunch with another female student in her 1st semester. For this, she activates 2 filters in the search criteria: gender and current semester. One hour before her lunchtime on Friday, she receives a notification that she has been matched with another student. Anastasiia reviews her profile and notices they both have a lot of different hobbies they could tell each other about. For example, Anastasiia's match is passionate about horse riding, which Anastasiia herself always wanted to learn more about.

Anastasiia loves diversity and meeting people from different backgrounds, so she happily accepts the invitation. After the other student confirms, they both receive a calendar invite via email. In the chat, they agree to meet at the entrance to the mensa. Unfortunately, at the very last moment, Anastasiia receives a message that the other student's train was cancelled and she can't make it to the Mensa that day. They agree to postpone their meetup for the next day, which they reconfirm and receive an updated calendar invite.

The following day, they finally meet up at the arranged time. The system automatically skips the matching process for Anastasiia on that day, since she already has a lunch partner. The meeting goes really well, and both students instantly click. They agree to meet up for lunch again next week, so Anastasiia deactivates her search on Mensa-Match, at least for the time being. The system has brilliantly fulfilled its purpose.

### Scenario 3
Enrico is a second-semester M.Sc. student in Informatics, and he's currently looking for group members for his Interdisciplinary Project. While most of his classmates are in Computer Science, he’s interested in meeting students from other departments to bring diverse perspectives to the project.

He signs into Mensa-Match and indicates that he’s particularly looking for 2 other people involved in the Interdisciplinary Project this semester. He selects lunchtime on Thursday and fills out his profile with relevant classes and hobbies, including his passion for design and urban mobility.

Enrico is matched with two other students, all of them accept the invitation, so Enrico receives an email with a calendar event for the meetup. They agree on a spot via the built-in chat feature and meet at the Mensa as planned.

During lunch, one of the AI-generated conversation starters suggests discussing project ideas around sustainability, a topic all three are passionate about. The group ends up brainstorming potential concepts, and by the end of the meal, Enrico has found two highly motivated partners for his project—and a reason to look forward to more team lunches.
