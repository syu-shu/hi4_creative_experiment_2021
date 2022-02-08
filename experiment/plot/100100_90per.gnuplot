set terminal "png"
set output "100100_90per.png"
# set logscale x
set nokey
set xrange [0:31]
# set yrange [0:4450]

plot 4425,4425*9/10,"../results/100/100/1642657951468" with lp pt 0, "../results/100/100/1642758379505" with lp pt 0, "../results/100/100/1642658468720" with lp pt 0, "../results/100/100/1642658495793" with lp pt 0, "../results/100/100/1642658546832" with lp pt 0