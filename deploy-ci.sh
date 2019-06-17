if [[ ${CI_COMMIT_MESSAGE} =~ ^\[maven-release-plugin\].+ ]]; then
  echo "Not deploying this commit"
else
  mvn ${MAVEN_CLI_OPTS} deploy
fi;
