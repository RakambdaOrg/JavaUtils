if [[ ${CI_COMMIT_MESSAGE} =~ ^\[maven-release-plugin\].+ ]]; then
  echo "Not deploying this commit"
else
  mvn ${MAVEN_CLI_OPTS} release:clean release:prepare release:perform release:clean
fi;
