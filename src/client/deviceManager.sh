# python3 Phone.py 1 &
# python3 Phone.py 2 &
# python3 Phone.py 3 &
# python3 Phone.py 4 &


for VARIABLE in 1 2 3 4 5
do
	python3 Phone.py $VARIABLE & 
	# command2
	# commandN
done