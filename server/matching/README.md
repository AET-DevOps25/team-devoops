# Meet@Mensa Matching Microservice

A Spring Boot application built with Gradle supporting the matching process in the Meet@Mensa app.

## Class Diagram
Diagram showing the basic class structure for the Matching Microservice

![Class Diagram](../../resources/diagrams/meetatmensa_uml_class_matching.png)

## The Meet@Mensa Matching Algorithm

### Goal
Match students into groups of 3-5, taking preferences into consideration, but prioritizing primarily including as many students in the match as possible.

### Step 0: Prepare Clusters

Students are assigned to "clusters" representing the potential start-times of an event.
Since events take 45 minutes, a student is added to cluster 1 (11:00 - 11:45) if they are available in time-slots 1 (11:00 - 11:15) , 2 (11:15-11:30) and 3 (11:30-11:45).
A single student can be assigned to multiple clusters.

### Step 1: Eliminate Dead Clusters

Meet@Mensa groups must be made up of at least 3 students. Clusters containing less than 2 students therefore are considered "dead" and are removed.
If removing a cluster would remove a student's last entry in any cluster, that student is considered unmatchable.

### Step 2: Determine Critical Candidate

Our priority is ensuring as many students are matched as possible.
To enable this, we next determine which user is in the fewest clusters, and therefore most at risk of becoming unmatchable.
In case of ties we select the most flexible student (with the fewest filters set) as critical.

### Step 3: Construct Minimal Group around Critical Candidate 

The algorithm then generates all possible 3-person groups that can include the critical candidate and checks them for quality.
Group quality is determined by the preferences of each user in said group.
For all users with preferences in the group, one "quality point" is awarded every time another student in the group fulfills one of their preferences.
Students without preferences are treated as if all their preferences were fulfilled.
The algorithm then selects the highest quality group that includes the Critical Candidate.

### Step 4: Remove Matched Users from Clusters

All entries corresponding to the 3 users are then removed from the clusters

### Step 5: Loop

Repeat Steps 1-4 until all clusters are empty

### Step 6: Attempt to fit Unmatchable Students

Finally, the algorithm check for any possibility to include students that were previously set as unmatchable into one of the matched groups.
If multiple such groups are possible, the one with the highest quality is picked.
Because of the nature of the clusters, at most 2 unmatchable users should left per cluster, meaning group sizes will not exceed 5.

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t ghcr.io/aet-devops25/team-devoops/server/matching .

# Push the image to GHCR (latest tag by default, access needed)
docker push ghcr.io/aet-devops25/team-devoops/server/matching

# Run the Docker container on port 8082
docker run --name matching-service -p 8082:80 ghcr.io/aet-devops25/team-devoops/server/matching

# List running containers (if needed) 
docker ps

# Stop the container     
docker stop matching-service

# Remove the container     
docker remove matching-service
```

The application will be available at `http://localhost:8082` and `http://localhost:8082/matching`.