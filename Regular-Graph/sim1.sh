cd sampleCone
echo "Semicone Samplng"
./rest.sh
#javac testGenericSMC.java
#java testGenericSMC > out-cone.txt
#cp ../Output/results.txt out.txt
#cp ../Output/output.txt out.txt

cd ../sampleCylinder
echo "Cylindrical Samplng"
./rest.sh
#javac testGenericSMC.java
#java testGenericSMC > out-cylinder.txt
#cp ../Output/output.txt out.txt
#cp ../Output/results.txt out.txt

cd ../sampleExhaustive
echo "Exhaustive Simulation"
./rest.sh
#javac testGenericSMC.java
#java testGenericSMC > out-exhaustive.txt
#cp ../Output/output.txt out.txt
#cp ../Output/results.txt out.txt
