# Contributing to Meet@Mensa

Thank you for your interest in contributing to Meet@Mensa! This document provides guidelines for commit messages and branch naming conventions to maintain a clean and consistent git history.

## Commit Message Guidelines

We follow the [Conventional Commits](https://www.conventionalcommits.org/) specification for our commit messages. This helps maintain a clean and consistent git history and enables automated versioning and changelog generation.

### Format

Each commit message consists of a **header** and an optional **body**:

```
<type>(<scope>): <description>

[optional body]

```

### Types

- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Changes that do not affect the meaning of the code (white-space, formatting, etc)
- `refactor`: A code change that neither fixes a bug nor adds a feature
- `perf`: A code change that improves performance
- `test`: Adding missing tests or correcting existing tests
- `chore`: Changes to the build process or auxiliary tools and libraries

### Scope

The scope is optional. If used, it should be the name of the module affected (e.g., `user`, `matching`, `ui`).

### Description

The description should be in the imperative mood and should not be capitalized. It should not end with a period.

### Examples

```
feat: display invitations on landing page
fix: remove incorrect API request
feat(auth): add email verification
fix(matching): resolve incorrect group size calculation
docs(readme): update installation instructions
```

## Branch Naming Guidelines

We use a structured approach to branch naming that helps identify the purpose and scope of changes.

### Format

```
<type>/<short-description>
```

### Types

- `feature`: New features or enhancements
- `bugfix`: Bug fixes
- `hotfix`: Urgent fixes for production issues
- `release`: Release preparation
- `docs`: Documentation updates
- `refactor`: Code refactoring
- `test`: Adding or updating tests

### Examples

```
feature/add-lunch-invitations
bugfix/fix-matching-algorithm
docs/update-api-documentation
```

### Best Practices

1. Use hyphens to separate words in the description
2. Keep descriptions concise but descriptive
3. Use lowercase letters
4. Avoid special characters
5. Link related issues in the pull request description

## Pull Request Guidelines

1. Create a new branch for each feature or bugfix
2. Follow the commit message guidelines for all commits
3. Create a pull request that uses our PR template
4. Include a clear description of changes
5. Reference any related issues or tickets in the PR description
6. Ensure all tests pass before submitting
