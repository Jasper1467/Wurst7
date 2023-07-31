@echo off

set /p original_repo_url="Enter the URL of the original repository: "
set /p pull_request_number="Enter the pull request number: "

REM Step 1: Ensure your fork is up to date
git remote add upstream "%original_repo_url%"
git fetch upstream
git checkout main
git merge upstream/main

REM Step 2: Apply the changes locally
git checkout main
git pull origin main
git checkout -b pr-branch
git pull "%original_repo_url%" "pull/%pull_request_number%/head"

REM Step 4: Resolve conflicts (if any)
REM Handle conflicts manually in the code editor

REM Step 5: Commit the changes
set /p commit_message="Enter a commit message for the merge: "
git add .
git commit -m "%commit_message%"

REM Step 6: Push the changes to your fork
git push origin pr-branch

REM Step 7: Create a pull request in your fork
echo.
echo To complete the process, go to your fork's repository on GitHub
echo and create a pull request for the 'pr-branch' branch.
