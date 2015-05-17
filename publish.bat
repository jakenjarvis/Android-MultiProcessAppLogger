git subtree pull --prefix=repository git@github.com:jakenjarvis/Android-MultiProcessAppLogger.git gh-pages --squash

call gradlew clean build uploadArchives

cd repository

git add -f .
git commit -m "update repository"

cd ..

git subtree push --prefix=repository git@github.com:jakenjarvis/Android-MultiProcessAppLogger.git gh-pages
git push origin master

pause
