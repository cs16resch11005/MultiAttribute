cd sampleCone
echo "Semicone Samplng"
rm *.class
javac testGenericSMC.java
#java testGenericSMC > out-cone.txt 
java testGenericSMC 
cp ../Output/results.txt out.txt
cp ../Output/output.txt out-cone.txt

cd ../sampleCylinder
echo "Cylindrical Samplng"
rm *.class
javac testGenericSMC.java
#java testGenericSMC > out-cylinder.txt
java testGenericSMC
cp ../Output/output.txt out-cylinder.txt
cp ../Output/results.txt out.txt

cd ../sampleExhaustive
echo "Exhaustive Simulation"
rm *.class
javac testGenericSMC.java
#java testGenericSMC > out-exhaustive.txt
java testGenericSMC 
cp ../Output/output.txt out-exhaustive.txt
cp ../Output/results.txt out.txt
