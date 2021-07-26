#sed -n '3,11p' reports/default_scenario_MessageStatsReport.txt >> overall1_0.txt
#cp output2.txt 2525.txt'''

javac testGenericSMC.java

#cp ../Input/atomic_prop_1.txt ../Input/AP/atomic_prop.txt 

OLD_STR=$(sed '16!d' ../Config/config.properties)
NEW_STR="sample_size = 1"
#echo $OLD_STR
echo $NEW_STR
sed  -i "16s/$OLD_STR/$NEW_STR/" ../Config/config.properties
java testGenericSMC
cp ../Output/results.txt 1.txt

OLD_STR=$(sed '16!d' ../Config/config.properties)
NEW_STR="sample_size = 4"
#echo $OLD_STR
echo $NEW_STR
sed  -i "16s/$OLD_STR/$NEW_STR/" ../Config/config.properties
java testGenericSMC
cp ../Output/results.txt 2.txt

OLD_STR=$(sed '16!d' ../Config/config.properties)
NEW_STR="sample_size = 8"
#echo $OLD_STR
echo $NEW_STR
sed  -i "16s/$OLD_STR/$NEW_STR/" ../Config/config.properties
java testGenericSMC
cp ../Output/results.txt 3.txt
