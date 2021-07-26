cd sampleCone-1
echo "Semicone Samplng-1"
rm *.class
javac testGenericSMC.java
java testGenericSMC > out-cone.txt
cp ../Output/results.txt out.txt
cp ../Output/output.txt out-cone.txt

cd ../sampleCylinder-1
echo "Cylindrical Samplng-1"
rm *.class
javac testGenericSMC.java
java testGenericSMC > out-cylinder.txt
cp ../Output/output.txt out-cylinder.txt
cp ../Output/results.txt out.txt

