set -x
mvn -T 4 -s .m2/settings.xml --batch-mode release:clean release:prepare
mvn -T 4 -s .m2/settings.xml --batch-mode release:perform
# mvn -T 4 -s .m2/settings.xml --batch-mode release:rollback
mvn -T 4 -s .m2/settings.xml --batch-mode release:clean
